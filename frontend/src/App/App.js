import './App.css';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import ServiceList from "../Service/ServiceList";
import ServiceEdit from "../Service/ServiceEdit";

export default function App() {
    return (
        <Router>
            <div>
                <Switch>
                    <Route path='/services/:id'>
                        <ServiceEdit/>
                    </Route>
                    <Route path="/services/new">
                        <ServiceEdit/>
                    </Route>
                    <Route path="/services">
                        <ServiceList/>
                    </Route>
                    <Route exact path="/">
                        <Redirect to="/services"/>
                    </Route>
                </Switch>
            </div>
        </Router>
    );
}
