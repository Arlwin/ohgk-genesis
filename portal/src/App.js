import { ThemeProvider } from '@mui/material/styles';
import React from 'react'

import { RouterProvider } from 'react-router-dom';
import router from './router/routes'
import theme from './theme/theme'

import Box from '@mui/material/Box';

import MainAppBar from './components/MainAppBar';
import LoginDialog from './components/LoginDialog';

export class App extends React.Component {

    constructor(props) {

        super(props);

        this.state = {
            login: false,
        }

        this.closeLogin = this.closeLogin.bind(this);
    }

    closeLogin() {

        this.setState({
            login: false
        });
    }

    render() {
        return (
            <ThemeProvider theme={theme}>
                <Box
                    sx={{
                        backgroundColor: 'background.main',
                        height: '100vh',
                    }}
                >
                    <MainAppBar />

                    <LoginDialog 
                        open = { this.state.login }
                        handleClose = { this.closeLogin }
                    />

                    <Box
                        sx={{
                            px: 5,
                        }}
                    >
                        <RouterProvider router={router} /> 
                    </Box>
                    
                </Box>
            </ThemeProvider>
        );
    }
}

export default App;
