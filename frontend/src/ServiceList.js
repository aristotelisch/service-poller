import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import moment from 'moment';
import {toast} from 'react-toastify';
import {Link} from "react-router-dom";
import Header from "./Header";

toast.configure();

class ServiceList extends Component {
    intervalId = 0;
    timeout = 15000;
    dateFormat = "dddd, MMMM Do YYYY, h:mm:ss a";

    constructor(props) {
        super(props);
        this.state = {services: []};
        this.remove = this.remove.bind(this);
        this.loadData = this.loadData.bind(this);
        this.notify = this.notify.bind(this);
    }

    async remove(id) {
        await fetch(`/api/services/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            debugger
            let updatedServices = [...this.state.services].filter(i => i.id !== id);
            this.setState({services: updatedServices});
            this.notify("Service removed", "success");
        });
    }

    notify(message, type) {
        toast(message, {type: type});
    }

    componentDidMount() {
        this.loadData();
        this.intervalId = setInterval(this.loadData, this.timeout);
    }

    componentWillUnmount() {
        clearInterval(this.intervalId);
    }

    async loadData() {
        try {
            const res = await fetch('/api/services');
            const data = await res.json();
            this.setState({services: data});

        } catch (e) {
            console.log(e);
        }
    }

    formatDate(date) {
        return moment(date, moment.ISO_8601)
            .format(this.dateFormat);
    }

    render() {
        const {services, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const serviceList = services.map((service, index) => {
            return <tr key={service.id}>
                <td>{index + 1}</td>
                <td style={{whiteSpace: 'nowrap'}}>{service.name}</td>
                <td>{service.url}</td>
                <td>{service.status}</td>
                <td>{this.formatDate(service.createdAt)}</td>
                <td>{this.formatDate(service.updatedAt)}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/services/" + service.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(service.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container>
                    <Header/>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/services/new">Add Service</Button>
                    </div>
                    <h3>Services</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="5%">Id</th>
                            <th width="20%">Name</th>
                            <th width="20%">Url</th>
                            <th width="5%">Status</th>
                            <th width="20%">Created At</th>
                            <th width="20%">Updated At</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {serviceList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default ServiceList;
