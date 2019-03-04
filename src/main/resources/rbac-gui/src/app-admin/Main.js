/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import React from "react";
import classNames from 'classnames';
import { MuiThemeProvider } from '@material-ui/core/styles';
import withStyles from "@material-ui/core/styles/withStyles";
import { Switch, Route, Redirect } from "react-router-dom";
import { TransitionGroup, CSSTransition } from 'react-transition-group';
import { theme, slideDuration, drawerWidth } from './style';
import background from './../assets/img/background.png';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import Icon from '@material-ui/core/Icon';
import Drawer from "@material-ui/core/Drawer";
import Divider from "@material-ui/core/Divider";

class Main extends React.Component {
    state = {
        menuOpen: true
    };
    createNavRoutes = (routes) => {
        const location = this.props.location;
        const classes = this.props.classes;
        return (
            <TransitionGroup>
                <CSSTransition key={location.key} timeout={slideDuration} classNames={{
                        enter: classes.slideEnter,
                        enterActive: classes.slideEnterActive,
                        exit: classes.slideExit,
                        exitActive: classes.slideExitActive
                }}>
                    <Switch location={location}>{this.createRoutes(routes, 0)}</Switch>
                </CSSTransition>
            </TransitionGroup>
        );
    };
    createRoutes = (routes, level) => {
        var arr = [];
        var startKey = (1000 * level) + 1;
        for (let prop of routes) {
            var currentKey = startKey++;
            if (!prop.path) {
                let nextLevel = level + 1;
                let childs = this.createRoutes(prop.children, nextLevel);
                for (let child of childs) {
                    arr.push(child);
                }
            } else if (prop.redirect) {
                arr.push(<Redirect from={prop.path} to={prop.to} key={currentKey} />);
            } else {
                arr.push(<Route path={prop.path} component={prop.component} key={currentKey} pathname={prop.path}/>);
            }
        }
        return arr;
    };
    handleDrawerToggle = () => {
        this.setState({ menuOpen: !this.state.menuOpen });
    };
    render() {
        const { classes } = this.props;
        const { menuOpen } = this.state;
        return (
            <MuiThemeProvider theme={theme}>
                <div className={classes.root}>
                    <AppBar position="fixed" className={classNames(classes.appBar, {
                            [classes.appBarShift]: menuOpen
                    })}>
                        <Toolbar>
                            { !menuOpen ? (
                                <IconButton color="inherit" aria-label="Menu" onClick={this.handleDrawerToggle}>
                                    <Icon>menu</Icon>
                                </IconButton>
                            ) : null}
                        </Toolbar>
                    </AppBar>
                    <Drawer anchor="left" className={classes.drawer} open={menuOpen} variant="persistent"
                        classes={{
                            paper: classes.drawerPaper
                        }}>
                        <div className={classes.drawerHeader}>
                            <IconButton onClick={this.handleDrawerToggle}>
                                <Icon>chevron_left</Icon>
                            </IconButton>
                        </div>
                        <Divider />
                    </Drawer>
                    <main className={classNames(classes.content, {
                        [classes.contentShift]: menuOpen
                    })}>
                            TODO
                    </main>
                </div>
            </MuiThemeProvider>
        );
    }
};

const styles = theme => ({
    wrapper: {
        position: "relative",
        top: "0",
        height: "100vh",
        overflowY: 'hidden',
        backgroundImage: "url(" + background + ")",
        backgroundSize: "cover",
        backgroundPosition: "center center",
        "&:after": {
            position: "absolute",
            zIndex: "3",
            width: "100%",
            height: "100%",
            content: '""',
            display: "block",
            background: '#d4d4d4',
            opacity: ".3"
        }
    },
    root: {
        display: 'flex',
        backgroundImage: "url(" + background + ")",
        backgroundSize: "cover",
        backgroundPosition: "center center",
        height: "100vh",
        "&:after": {
            position: "absolute",
            zIndex: "3",
            width: "100%",
            height: "100%",
            content: '""',
            display: "block",
            background: '#d4d4d4',
            opacity: ".3"
        }
    },
    appBar: {
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen
        })
    },
    appBarShift: {
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: drawerWidth,
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen
        })
    },
    drawerPaper: {
        width: drawerWidth
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0
    },
    drawerHeader: {
        display: 'flex',
        alignItems: 'center',
        padding: '0 8px',
        ...theme.mixins.toolbar,
        justifyContent: 'flex-end'
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen
        }),
        marginLeft: -drawerWidth
    },
    contentShift: {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen
        }),
        marginLeft: 0
    }
});

export default withStyles(styles)(Main);
