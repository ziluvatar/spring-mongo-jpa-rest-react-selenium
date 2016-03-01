var Modal = ReactBootstrap.Modal;
var Button = ReactBootstrap.Button;
var ButtonInput = ReactBootstrap.ButtonInput;
var update = React.addons.update;

var CompanyList = React.createClass({
  render: function() {
    var companyNodes = this.props.companies.map(function(company) {
      return (
        <tr className="companyRow">
          <td>{company.name}</td>
          <td>{company.address}</td>
          <td>{company.city}</td>
          <td>{company.country}</td>
          <td>{company.email}</td>
          <td>{company.phone}</td>
          <td>{company.owners.join(", ")}</td>
        </tr>
      );
    });

    return (
      <div className="companyList">
        <table className="table">
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
          {companyNodes}
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
      url: "/companies",
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({
        name: this.state.values.name,
        address: this.state.values.address,
        city: this.state.values.city,
        country: this.state.values.country,
        email: this.state.values.email,
        phone: this.state.values.phone,
        owners: this.parseOwners(this.state.values.owners)
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
            <Modal.Title>Create a company</Modal.Title>
          </Modal.Header>
          <form name="companyForm" role="form" onSubmit={this.handleSubmit}>
            <Modal.Body>
              <FormGroup name="name" label="Name (*)" hint="Enter a name" 
                         value={this.state.values.name} error={this.state.errors.name}
                         onInputChange={this.handleInputChange('name')} />
              <FormGroup name="address" label="Address (*)" hint="Enter an address"
                         value={this.state.values.address} error={this.state.errors.address}
                         onInputChange={this.handleInputChange('address')} />
              <div className="row">
                <div className="col-md-6">
                  <FormGroup name="city" label="City (*)" hint="Enter a city"
                             value={this.state.values.city} error={this.state.errors.city}
                             onInputChange={this.handleInputChange('city')} />
                </div>
                <div className="col-md-6">
                  <FormGroup name="country" label="Country (*)" hint="Enter a country"
                             value={this.state.values.country} error={this.state.errors.country}
                             onInputChange={this.handleInputChange('country')} />
                </div>
              </div>
              <div className="row">
                <div className="col-md-6">
                  <FormGroup name="email" label="Email" hint="Enter an email"
                             value={this.state.values.email} error={this.state.errors.email}
                             onInputChange={this.handleInputChange('email')} />
                </div>
                <div className="col-md-6">
                  <FormGroup name="phone" label="Phone" hint="Enter a phone"
                             value={this.state.values.phone} error={this.state.errors.phone}
                             onInputChange={this.handleInputChange('phone')} />
                </div>
              </div>
              <FormGroup name="owners" label="Owners (*)" hint="Enter a owners"
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


var CompaniesBox = React.createClass({
  getInitialState: function() {
    return {
      showCompanyForm: false,
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
    this.setState({ showCompanyForm:false });
    this.fetchCompanies();
  },
  componentDidMount: function() {
    this.fetchCompanies();
  },
  render: function() {
    var companyForm;

    if (this.state.showCompanyForm) {
      companyForm = <CompanyForm onFinished={this.handleCompanyFormFinished}/>
    }
    return (
      <div>
        <div className="global-actions pull-right">
          <button className="btn btn-primary" onClick={this.handleNewCompanyClick}>New Company</button>
        </div>
        <CompanyList companies={this.state.companies} />

        {companyForm}
      </div>
    );
  }
});

ReactDOM.render(
  <CompaniesBox url="/companies" />,
  document.getElementById('companies-content')
);