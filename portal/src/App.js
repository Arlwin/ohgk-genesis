import React from 'react'

import Box from '@mui/material/Box';
import LoginDialog from './components/LoginDialog';

export class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            login: true,
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
            <Box>
                <LoginDialog 
                    open = { this.state.login }
                    handleClose = { this.closeLogin }
                />
            </Box>
        );
    }


}

export default App;
