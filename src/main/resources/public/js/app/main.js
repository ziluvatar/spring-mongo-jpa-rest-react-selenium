var CompanyList = App.Components.CompanyList;
var OwnersForm = App.Components.OwnersForm;
var CompanyForm = App.Components.CompanyForm;

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
    var resourceUrl;

    if (this.state.currentCompanyId) {
      resourceUrl = this.props.url + '/' + this.state.currentCompanyId;
    }

    return (
      <div>
        <div className="global-actions pull-right">
          <button className="btn btn-primary" onClick={this.handleNewCompanyClick}>New Company</button>
        </div>
        <CompanyList companies={this.state.companies}
                     onCompanyEditRequested={this.handleCompanyEditRequested}
                     onAddOwnersRequested={this.handleAddOwnersRequested} />

        <CompanyForm url={resourceUrl} open={this.state.showCompanyForm}
                     onClose={this.handleCompanyFormFinished} />

        <OwnersForm url={resourceUrl} open={this.state.showOwnersForm}
                    onClose={this.handleOwnersFormFinished} />
      </div>
    );
  }
});

ReactDOM.render(
  <CompaniesBox url="/companies" />,
  document.getElementById('companies-content')
);