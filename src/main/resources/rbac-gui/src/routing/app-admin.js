/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import { AppURLs } from './urls'; 
import Users from './../app-admin/page/Users';
import Roles from './../app-admin/page/Roles';

const navigationRoutes = [{
        path: AppURLs.links.page + "/users",
        name: "users",
        icon: 'person',
        component: Users
    }, {
        path: AppURLs.links.page + "/roles",
        name: "roles",
        icon: 'group',
        component: Roles
    }, {
        redirect: true,
        path: AppURLs.context,
        to: AppURLs.links.page + "/users",
        navbarName: "Redirect"
    }];

export default navigationRoutes;