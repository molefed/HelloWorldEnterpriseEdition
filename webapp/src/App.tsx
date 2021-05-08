import {Redirect, Route, Switch} from "react-router-dom";
import React from 'react';
import {ROUTES} from "./constants/routes";
import Layout from "./components/Layout";
import {CustomersPage, DashboardPage, InventoryPage, OrdersPage,} from "./pages";
import SignIn from './components/login/SignIn';
import useToken from './useToken';

function App() {
    const {token, setToken} = useToken();

    if (!token) {
        return <SignIn setToken={setToken}/>
    }

    return (
        <Layout setToken={setToken}>
            <Switch>
                <Route path="/" exact render={() => <Redirect to={ROUTES.main}/>}/>
                <Route exact path={ROUTES.main} component={DashboardPage}/>
                <Route exact path={ROUTES.orders} component={OrdersPage}/>
                <Route exact path={ROUTES.customers} component={CustomersPage}/>
                <Route exact path={ROUTES.inventory} component={InventoryPage}/>
            </Switch>
        </Layout>
    );
}

export default App;
