var CompanyList = React.createClass({
  render: function() {
    var companyNodes = this.props.companies.map(function(company) {
      return (
        <tr class="companyRow">
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

var CompaniesBox = React.createClass({
  getInitialState: function() {
    return {companies: []};
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
  componentDidMount: function() {
    this.fetchCompanies();
    setInterval(this.fetchCompanies, this.props.pollInterval);
  },
  render: function() {
    return (
      <div>
        <CompanyList companies={this.state.companies} />
      </div>
    );
  }
});

ReactDOM.render(
  <CompaniesBox url="/companies" pollInterval={2000} />,
  document.getElementById('companies-content')
);