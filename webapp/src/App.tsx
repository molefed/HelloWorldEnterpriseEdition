import {Redirect, Route, Switch} from "react-router-dom";
import React from 'react';
import {ROUTES} from "./constants/routes";
import Layout from "./components/Layout";
import {DashboardPage, InventoryPage, OrdersPage, UsersPage,} from "./pages";
import SignIn from './components/login/SignIn';
import SignUp from './components/login/SignUp';
import {getToken} from './service/TokenFolderService';

export default function App() {
    const token = getToken();

    if (!token) {
        return (
            <Switch>
                <Route path="/" exact render={() => <Redirect to={"/signin"}/>}/>
                <Route exact path="/signin" component={() => <SignIn />}/>
                <Route exact path="/signup" component={() => <SignUp />}/>
            </Switch>
        );
    }

    return (
        <Layout>
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
