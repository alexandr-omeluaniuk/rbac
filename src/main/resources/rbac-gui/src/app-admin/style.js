/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import { createMuiTheme } from '@material-ui/core/styles';
import pink from '@material-ui/core/colors/pink';
import deepPurple from '@material-ui/core/colors/deepPurple';
import 'typeface-roboto';

const theme = createMuiTheme({
    palette: {
        primary: pink,
        secondary: deepPurple,
        white: 'white'
    },
    typography: {
        useNextVariants: true
    }
});
theme.palette.primary.main = pink['800'];
console.log(theme);
const boxShadow = {
    boxShadow: "0 10px 30px -12px rgba(0, 0, 0, 0.42), 0 4px 25px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(0, 0, 0, 0.2)"
};
const drawerWidth = 320;
const slideDuration = 1000;
const transition = {
    transition: "all 0.33s cubic-bezier(0.685, 0.0473, 0.346, 1)"
};

export { theme, boxShadow, drawerWidth, transition, slideDuration };
