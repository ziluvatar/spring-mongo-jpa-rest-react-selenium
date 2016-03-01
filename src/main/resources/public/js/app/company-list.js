(function() {

  window.App = window.App || { Components: {} };

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

  App.Components.CompanyList = React.createClass({
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

})();