import {Redirect, Route, Switch} from "react-router-dom";
import React from 'react';
import {ROUTES} from "./constants/routes";
import Layout from "./components/Layout";
import {DashboardPage, InventoryPage, OrdersPage, UsersPage,} from "./pages";
import SignIn from './components/login/SignIn';
import SignUp from './components/login/SignUp';
import useToken from './useToken';

export default function App() {
    const {token, setToken} = useToken();

    if (!token) {
        return (
            <Switch>
                <Route path="/" exact render={() => <Redirect to={"/signin"}/>}/>
                <Route exact path="/signin" component={() => <SignIn setToken={setToken}/>}/>
                <Route exact path="/signup" component={() => <SignUp setToken={setToken}/>}/>
            </Switch>
        );
    }

    return (
        <Layout setToken={setToken}>
            <Switch>
                <Route path="/" exact render={() => <Redirect to={ROUTES.main}/>}/>
                <Route exact path={ROUTES.main} component={DashboardPage}/>
                <Route exact path={ROUTES.orders} component={OrdersPage}/>
                <Route exact path={ROUTES.users} component={UsersPage}/>
                <Route exact path={ROUTES.inventory} component={InventoryPage}/>
            </Switch>
        </Layout>
    );
}
