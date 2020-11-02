import React from 'react';
import { Switch, Route, Link } from "react-router-dom";
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';

import { useStyles } from './App.styles';
import UserProfile from "./components/UserProfile.js";

import Users from './pages/Users';

function App() {
    const classes = useStyles();
    return (
        <React.Fragment>
            <AppBar position={'static'}>
                <Toolbar>
                    <Link to={'/'} className={classes.menuLink}>Home</Link>
                    <Link to={'/users'} className={classes.menuLink}>Users</Link>
                    <UserProfile/>
                </Toolbar>
            </AppBar>
            <div>
                <Switch>
                    <Route path={'/'} component={Users} exact={true}/>
                    <Route path={'/users'} component={Users} exact={true}/>
                </Switch>
            </div>
        </React.Fragment>
    )
}

export default App;