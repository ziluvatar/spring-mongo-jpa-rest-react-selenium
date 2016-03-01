var Modal = ReactBootstrap.Modal;
var Button = ReactBootstrap.Button;
var ButtonInput = ReactBootstrap.ButtonInput;
var update = React.addons.update;

var CompanyListRow = React.createClass({
  handleEditButtonClick: function () {
    this.props.onCompanyEditButtonClick(this.props.company);
  },
  handleAddOwnersButtonClick: function () {
    this.props.onAddOwnersButtonClick(this.props.company);
  },
  render: function () {
    return (
      <tr className="companyRow">
        <td>{this.props.company.name}</td>
        <td>{this.props.company.address}</td>
        <td>{this.props.company.city}</td>
        <td>{this.props.company.country}</td>
        <td>{this.props.company.email}</td>
        <td>{this.props.company.phone}</td>
        <td>{this.props.company.owners.join(", ")}</td>
        <td>
          <button className="btn btn-primary btn-sm" onClick={this.handleEditButtonClick}>Edit</button>
          <button className="btn btn-default btn-sm btn-rightOf-inline" onClick={this.handleAddOwnersButtonClick}>Add Owners</button>
        </td>
      </tr>
    );
  }
});
var CompanyList = React.createClass({
  render: function() {
    var i;
    var companyNodes = [];

    for (i = 0; i < this.props.companies.length; i++) {
      companyNodes.push(
        <CompanyListRow company={this.props.companies[i]}
                        onCompanyEditButtonClick={this.props.onCompanyEditRequested}
                        onAddOwnersButtonClick={this.props.onAddOwnersRequested} />
      );
    }

    return (
      <div className="companyList">
        <table className="table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Address</th>
              <th>City</th>
              <th>Country</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Owners</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {companyNodes}
          </tbody>
        </table>
      </div>
    );
  }
});

var FormGroup = React.createClass({
  getErrorMessage: function(code) {
    switch (code) {
      case 'NotEmpty':
        return 'This field can not be empty';
      case 'Email':
        return 'The email format is invalid';
    }
  },
  render: function(){
    var errorMessage, groupErrorClass;
    var formGroupClass = 'form-group-' + this.props.name;

    if (this.props.error) {
      errorMessage = <span className="help-block error-message">{this.getErrorMessage(this.props.error)}</span>;
      groupErrorClass = 'has-error';
    }
    return (
      <div className={ formGroupClass + ' form-group ' + groupErrorClass}>
        <label htmlFor={'companyForm' + this.props.name} className="control-label">{this.props.label}</label>
        <input type="text" className="form-control" id={'companyForm' + this.props.name} name={this.props.name}
               placeholder={this.props.hint} value={this.props.value} onChange={this.props.onInputChange} />
        {errorMessage}
      </div>
    );
  }
});

var OwnersForm = React.createClass({
  getInitialState: function () {
    return {
      showModal: true,
      errors: {
        owners: ''
      },
      values: {
        owners: ''
      }
    };
  },
  parseOwners: function (ownersString) {
    if (ownersString) {
      return ownersString.replace(/,\s+/g,',').split(',');
    } else {
      return null;
    }
  },
  handleSubmit: function(e) {
    e.preventDefault();

    $.ajax({
      url: this.props.url + '/owners',
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({
        owners: this.parseOwners(this.state.values.owners)
      }),
      success: function() {
        this.setState(this.getInitialState());
        this.close();
      }.bind(this),
      error: function(xhr, status, err) {
        if (xhr.status === 400) {
          if (xhr.responseJSON.errors) {
            this.handleErrors(xhr.responseJSON.errors);
          }
        }
      }.bind(this)
    });
  },
  handleErrors: function(errors) {
    var i;
    var stateErrors = {};
    for (i = 0; i < errors.length; i++) {
      stateErrors[errors[i].field] = errors[i].code;
    }
    this.setState({ errors: update(this.state.errors, { $merge: stateErrors })});
  },
  close: function() {
    this.setState({ showModal: false });
    this.props.onFinished();
  },
  open: function() {
    this.setState({ showModal: true });
  },
  handleInputChange: function(fieldName) {
    var self = this;
    return function(e) {
      var errors = {};
      var values = {};

      values[fieldName] = e.target.value;
      errors[fieldName] = '';
      self.setState({
        errors: update(self.state.errors, { $merge: errors }),
        values: update(self.state.values, { $merge: values })
      });
    }
  },
  render: function() {
    return (
      <Modal show={this.state.showModal} onHide={this.close}>
        <Modal.Header closeButton>
          <Modal.Title>Add new owners</Modal.Title>
        </Modal.Header>
        <form name="ownersForm" role="form" onSubmit={this.handleSubmit}>
          <Modal.Body>
            <FormGroup name="owners" label="Owners (*)" hint="Enter owner(s) separated by comma"
                       value={this.state.values.owners} error={this.state.errors.owners}
                       onInputChange={this.handleInputChange('owners')} />
            <span>(*) Required field</span>
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={this.close}>Close</Button>
            <input type="submit" className="btn btn-primary" value="Save" />
          </Modal.Footer>
        </form>
      </Modal>

    );
  }
});

var CompanyForm = React.createClass({
  getInitialState: function () {
    return {
      showModal: true,
      errors: {
        name: '',
        address: '',
        city: '',
        country: '',
        email: '',
        phone: '',
        owners: ''
      },
      values: {
        name: '',
        address: '',
        city: '',
        country: '',
        email: '',
        phone: '',
        owners: ''
      }
    };
  },
  componentDidMount: function() {
    this.fetchCompany(this.props.url);
  },
  parseOwnersString: function (ownersString) {
    return ownersString ? ownersString.replace(/,\s+/g, ',').split(',') : null;
  },
  fetchCompany: function (url) {
    $.ajax({
      url: url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        var values = update(data, { owners: { $set: data.owners.join(', ') } });
        this.setState({ values: values });
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  handleSubmit: function(e) {
    var url = '/companies', method = 'POST';
    e.preventDefault();

    if (this.props.url) {
      url = this.props.url;
      method = 'PUT';
    }

    $.ajax({
      url: url,
      method: method,
      contentType: 'application/json',
      data: JSON.stringify({
        name: this.state.values.name,
        address: this.state.values.address,
        city: this.state.values.city,
        country: this.state.values.country,
        email: this.state.values.email,
        phone: this.state.values.phone,
        owners: this.parseOwnersString(this.state.values.owners)
      }),
      success: function(data) {
        this.setState(this.getInitialState());
        this.close();
      }.bind(this),
      error: function(xhr, status, err) {
        if (xhr.status === 400) {
          if (xhr.responseJSON.errors) {
            this.handleErrors(xhr.responseJSON.errors);
          }
        }
      }.bind(this)
    });
  },
  handleErrors: function(errors) {
    var i;
    var stateErrors = {};
    for (i = 0; i < errors.length; i++) {
      stateErrors[errors[i].field] = errors[i].code;
    }
    this.setState({ errors: update(this.state.errors, { $merge: stateErrors })});
  },
  close: function() {
    this.setState({ showModal: false });
    this.props.onFinished();
  },
  open: function() {
    this.setState({ showModal: true });
  },
  handleInputChange: function(e) {
    var errors = {};
    var values = {};
    console.log(e.target.name + ': ' + e.target.value);
    values[e.target.name] = e.target.value;
    errors[e.target.name] = '';
    this.setState({
      errors: update(this.state.errors, { $merge: errors }),
      values: update(this.state.values, { $merge: values })
    });
  },
  render: function() {
    return (
        <Modal show={this.state.showModal} onHide={this.close}>
          <Modal.Header closeButton>
            <Modal.Title>Create a company</Modal.Title>
          </Modal.Header>
          <form name="companyForm" role="form" onSubmit={this.handleSubmit}>
            <Modal.Body>
              <FormGroup name="name" label="Name (*)" hint="Enter a name" 
                         value={this.state.values.name} error={this.state.errors.name}
                         onInputChange={this.handleInputChange} />
              <FormGroup name="address" label="Address (*)" hint="Enter an address"
                         value={this.state.values.address} error={this.state.errors.address}
                         onInputChange={this.handleInputChange} />
              <div className="row">
                <div className="col-md-6">
                  <FormGroup name="city" label="City (*)" hint="Enter a city"
                             value={this.state.values.city} error={this.state.errors.city}
                             onInputChange={this.handleInputChange} />
                </div>
                <div className="col-md-6">
                  <FormGroup name="country" label="Country (*)" hint="Enter a country"
                             value={this.state.values.country} error={this.state.errors.country}
                             onInputChange={this.handleInputChange} />
                </div>
              </div>
              <div className="row">
                <div className="col-md-6">
                  <FormGroup name="email" label="Email" hint="Enter an email"
                             value={this.state.values.email} error={this.state.errors.email}
                             onInputChange={this.handleInputChange} />
                </div>
                <div className="col-md-6">
                  <FormGroup name="phone" label="Phone" hint="Enter a phone"
                             value={this.state.values.phone} error={this.state.errors.phone}
                             onInputChange={this.handleInputChange} />
                </div>
              </div>
              <FormGroup name="owners" label="Owners (*)" hint="Enter owner(s) separated by comma"
                         value={this.state.values.owners} error={this.state.errors.owners}
                         onInputChange={this.handleInputChange} />
              <span>(*) Required field</span>
            </Modal.Body>
            <Modal.Footer>
              <Button onClick={this.close}>Close</Button>
              <input type="submit" className="btn btn-primary" value="Save" />
            </Modal.Footer>
          </form>
        </Modal>

    );
  }
});


var CompaniesBox = React.createClass({
  getInitialState: function() {
    return {
      showCompanyForm: false,
      showOwnersForm: false,
      currentCompanyId: null,
      companies: []
    };
  },
  fetchCompanies: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({companies: data.companies});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  handleNewCompanyClick: function () {
    this.setState({ showCompanyForm:true });
  },
  handleCompanyFormFinished: function () {
    this.setState({ showCompanyForm:false, currentCompanyId: null });
    this.fetchCompanies();
  },
  handleOwnersFormFinished: function () {
    this.setState({ showOwnersForm:false, currentCompanyId: null });
    this.fetchCompanies();
  },
  handleCompanyEditRequested: function (company) {
    this.setState({ showCompanyForm: true, currentCompanyId: company.id });
  },
  handleAddOwnersRequested: function (company) {
    this.setState({ showOwnersForm: true, currentCompanyId: company.id });
  },
  componentDidMount: function() {
    this.fetchCompanies();
  },
  render: function() {
    var companyForm, ownersForm, resourceUrl;

    if (this.state.currentCompanyId) {
      resourceUrl = this.props.url + '/' + this.state.currentCompanyId;
    }

    if (this.state.showCompanyForm) {
      companyForm = <CompanyForm url={resourceUrl} onFinished={this.handleCompanyFormFinished}/>
    }

    if (this.state.showOwnersForm) {
      ownersForm = <OwnersForm url={resourceUrl} onFinished={this.handleOwnersFormFinished} />
    }
    return (
      <div>
        <div className="global-actions pull-right">
          <button className="btn btn-primary" onClick={this.handleNewCompanyClick}>New Company</button>
        </div>
        <CompanyList companies={this.state.companies}
                     onCompanyEditRequested={this.handleCompanyEditRequested}
                     onAddOwnersRequested={this.handleAddOwnersRequested} />

        {companyForm}
        {ownersForm}
      </div>
    );
  }
});

ReactDOM.render(
  <CompaniesBox url="/companies" />,
  document.getElementById('companies-content')
);