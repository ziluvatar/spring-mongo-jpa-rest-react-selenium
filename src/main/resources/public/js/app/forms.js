(function() {

  var Modal = ReactBootstrap.Modal;
  var Button = ReactBootstrap.Button;
  var update = React.addons.update;

  window.App = window.App || { Components: {} };

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

  function parseOwnersString(ownersString) {
    return ownersString ? ownersString.replace(/,\s+/g, ',').split(',') : null;
  }

  function updateStateWithErrors(currentState, errors) {
    var i;
    var stateErrors = {};
    for (i = 0; i < errors.length; i++) {
      stateErrors[errors[i].field] = errors[i].code;
    }
    return { errors: update(currentState.errors, { $merge: stateErrors })};
  }

  function updateStateOnFieldChange(currentState, fieldName, fieldValue) {
    var errors = {};
    var values = {};

    values[fieldName] = fieldValue;
    errors[fieldName] = '';
    return {
      errors: update(currentState.errors, { $merge: errors }),
      values: update(currentState.values, { $merge: values })
    };
  }

  window.App.Components.OwnersForm = React.createClass({
    getInitialState: function () {
      return {
        errors: { owners: '' },
        values: { owners: '' }
      };
    },
    handleSubmit: function(e) {
      e.preventDefault();

      $.ajax({
        url: this.props.url + '/owners',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
          owners: parseOwnersString(this.state.values.owners)
        }),
        success: function() {
          this.setState(this.getInitialState());
          this.props.onClose();
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
      this.setState(updateStateWithErrors(this.state, errors));
    },
    handleInputChange: function(e) {
      this.setState(
        updateStateOnFieldChange(this.state, e.target.name, e.target.value));
    },
    render: function() {
      return (
        <Modal show={this.props.open} onHide={this.props.onClose} animation={false}>
          <Modal.Header closeButton>
            <Modal.Title>Add new owners</Modal.Title>
          </Modal.Header>
          <form name="ownersForm" role="form" onSubmit={this.handleSubmit}>
            <Modal.Body>
              <FormGroup name="owners" label="Owners (*)" hint="Enter owner(s) separated by comma"
                         value={this.state.values.owners} error={this.state.errors.owners}
                         onInputChange={this.handleInputChange} />
              <span>(*) Required field</span>
            </Modal.Body>
            <Modal.Footer>
              <Button onClick={this.props.onClose}>Close</Button>
              <input type="submit" className="btn btn-primary" value="Save" />
            </Modal.Footer>
          </form>
        </Modal>

      );
    }
  });

  window.App.Components.CompanyForm = React.createClass({
    getInitialState: function () {
      return {
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
    componentWillReceiveProps: function(nextProps) {
      this.setState(this.getInitialState());
      if (nextProps.url) {
        this.fetchCompany(nextProps.url);
      }
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
          owners: parseOwnersString(this.state.values.owners)
        }),
        success: function(data) {
          this.setState(this.getInitialState());
          this.props.onClose();
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
      this.setState(updateStateWithErrors(this.state, errors));
    },
    handleInputChange: function(e) {
      this.setState(
        updateStateOnFieldChange(this.state, e.target.name, e.target.value));
    },
    render: function() {
      return (
        <Modal show={this.props.open} onHide={this.props.onClose} animation={false}>
          <Modal.Header closeButton>
            <Modal.Title>Enter company information</Modal.Title>
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
              <Button onClick={this.props.onClose}>Close</Button>
              <input type="submit" className="btn btn-primary" value="Save" />
            </Modal.Footer>
          </form>
        </Modal>

      );
    }
  });

})();