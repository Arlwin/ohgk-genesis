import { createTheme } from "@mui/material";

const theme = createTheme({

    palette: {
        type: 'light',
        primary: {
            main: '#222831'
        },
        secondary: {
            main: '#B55400',
        },
        background: {
            main: '#EEEEEE'
        }
    },
    typography: {
        fontFamily: 'Montserrat',
    },
});

export default theme;