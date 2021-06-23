import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import {toast} from "react-toastify";
import Header from "../Common/Header";

toast.configure();

class ServiceEdit extends Component {

    emptyItem = {
        name: '',
        url: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            console.log('got param id', this.props.match.params.id)
            const service = await (await fetch(`/api/services/${this.props.match.params.id}`)).json();
            this.setState({item: service});
        }
    }

    notify(message, type) {
        toast(message, {type: type});
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/api/services' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        }).then(response => {
            console.log('response', response);
            if (response.status >= 400) {
                this.notify('Action failed', "error");
            } else {
                this.notify(item.id ? 'Service Updated' : 'Service Added', 'success')
            }
        } );
        this.props.history.push('/services');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Service' : 'Add Service'}</h2>;

        return <div>
            <Container>
                <Header />
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="url">Url</Label>
                        <Input type="text" name="url" id="url" value={item.url || ''}
                               onChange={this.handleChange} autoComplete="url"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/services">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}
export default withRouter(ServiceEdit);
