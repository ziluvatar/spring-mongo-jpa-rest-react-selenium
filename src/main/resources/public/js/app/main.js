var Modal = ReactBootstrap.Modal;
var Button = ReactBootstrap.Button;
var ButtonInput = ReactBootstrap.ButtonInput;

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

var CompanyForm = React.createClass({
  getInitialState: function () {
    return {
      showModal: true,
      name: '',
      address: '',
      city: '',
      country: '',
      email: '',
      phone: '',
      owners: ''
    };
  },
  handleSubmit: function(e) {
    e.preventDefault();

    $.ajax({
      url: "/companies",
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({
        name: this.state.name,
        address: this.state.address,
        city: this.state.city,
        country: this.state.country,
        email: this.state.email,
        phone: this.state.phone,
        owners: this.state.owners.split(",")
      }),
      success: function(data) {
        this.setState(this.getInitialState());
        this.close();
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  handleNameChanged: function (e) {
    this.setState({ name: e.target.value });
  },
  handleAddressChanged: function (e) {
    this.setState({ address: e.target.value });
  },
  handleCityChanged: function (e) {
    this.setState({ city: e.target.value });
  },
  handleCountryChanged: function (e) {
    this.setState({ country: e.target.value });
  },
  handleEmailChanged: function (e) {
    this.setState({ email: e.target.value });
  },
  handlePhoneChanged: function (e) {
    this.setState({ phone: e.target.value });
  },
  handleOwnersChanged: function (e) {
    this.setState({ owners: e.target.value });
  },
  close() {
    this.setState({ showModal: false });
    this.props.onFinished();
  },
  open() {
    this.setState({ showModal: true });
  },
  render: function() {
    return (
        <Modal show={this.state.showModal} onHide={this.close}>
          <Modal.Header closeButton>
            <Modal.Title>Create a company</Modal.Title>
          </Modal.Header>
          <form name="companyForm" role="form" onSubmit={this.handleSubmit}>
            <Modal.Body>
              <div className="form-group">
                <label htmlFor="companyFormName" className="control-label">Name (*)</label>
                <input type="text" className="form-control" id="companyFormName" name="name"
                       placeholder="Enter a name" value={this.state.name} onChange={this.handleNameChanged} />
              </div>
              <div className="form-group">
                <label htmlFor="companyFormAddress" className="control-label">Address (*)</label>
                <input type="text" className="form-control" id="companyFormAddress" name="address"
                       placeholder="Enter an address" value={this.state.address} onChange={this.handleAddressChanged} />
              </div>
              <div className="form-group">
                <label htmlFor="companyFormCity" className="control-label">City (*)</label>
                <input type="text" className="form-control" id="companyFormCity" name="city"
                       placeholder="Enter a city" value={this.state.city} onChange={this.handleCityChanged} />
              </div>
              <div className="form-group">
                <label htmlFor="companyFormCountry" className="control-label">Country (*)</label>
                <input type="text" className="form-control" id="companyFormCountry" name="country"
                       placeholder="Enter a country" value={this.state.country} onChange={this.handleCountryChanged} />
              </div>
              <div className="form-group">
                <label htmlFor="companyFormEmail" className="control-label">Email</label>
                <input type="text" className="form-control" id="companyFormEmail" name="email"
                       placeholder="Enter an email" value={this.state.email} onChange={this.handleEmailChanged} />
              </div>
              <div className="form-group">
                <label htmlFor="companyFormPhone" className="control-label">Phone</label>
                <input type="text" className="form-control" id="companyFormPhone" name="phone"
                       placeholder="Enter an phone" value={this.state.phone} onChange={this.handlePhoneChanged} />
              </div>
              <div className="form-group">
                <label htmlFor="companyFormOwners" className="control-label">Owners (*)</label>
                <input type="text" className="form-control" id="companyFormOwners" name="owners"
                       placeholder="Enter some owners separated by comma" value={this.state.owners} onChange={this.handleOwnersChanged} />
              </div>
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