import React from 'react'

import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import HttpService from '../services/HttpService';

const EMPTY_ERROR = "This field is required."
const INCORRECT_CREDENTIALS_ERROR = "Username / Password is incorrect."

export class LoginDialog extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: '',

            usernameError: true,
            passwordError: true,
            credentialsIncorrect: false,
        }

        this.login = this.login.bind(this);
        this.handleInput = this.handleInput.bind(this);
        this.isEmpty = this.isEmpty.bind(this);
    }

    textFieldCss = {
        my: 1.5,
    }

    login() {

        HttpService.get(
            '/users/login',
            {
                auth: {
                    'username': this.state.username,
                    'password': this.state.password
                }
            }
        ).then(
            (response) => {

                this.setState({
                    credentialsIncorrect: false,
                })

                console.log(response);
            }
        ).catch(
            (error) => {

                if (error.request.status === 401) {

                    this.setState({
                        credentialsIncorrect: true,
                    })
                } else {

                    console.error(error);
                }
            }
        )
    }

    handleInput(input) {

        const target = input['target'];
        const name = target['name'];
        const value = target['value'];
        const errorName = name + 'Error'

        this.setState({
            [name]: value,
            [errorName]: this.isEmpty(value)
        });
    }

    isEmpty(input) {

        return input.toString().trim() === "";
    }

    render() {

        return (
            <Modal
                open = {this.props.open}
                onClose = {this.props.handleClose}
            >
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        bgcolor: 'background.paper',
                        borderRadius: '5px',
                        outline: 0,
                        boxShadow: 12,
                        p: 4,
                    }}
                >
                    <Box
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                        }}
                    >
                        <Typography 
                            variant='h4'
                            sx={{
                                alignSelf: 'center'
                            }}
                        >
                            LOG IN
                        </Typography>

                        <Box 
                            sx={{
                                height: '24px'
                            }}
                        />

                        <TextField
                            name='username'
                            label="Username" 
                            variant="outlined"
                            sx={this.textFieldCss}
                            onChange={this.handleInput}
                            value={this.state.username}
                            error={this.state.usernameError}
                            helperText={this.state.usernameError ? EMPTY_ERROR : ""}
                        />

                        <TextField 
                            name='password'
                            label="Password" 
                            variant="outlined" 
                            sx={this.textFieldCss}
                            type='password'
                            onChange={this.handleInput}
                            value={this.state.password}
                            error={this.state.passwordError}
                            helperText={this.state.passwordError ? EMPTY_ERROR : ""}
                        />

                        {this.state.credentialsIncorrect && 
                            <Typography
                                variant="caption"
                                color="error"
                                sx={{
                                    alignSelf: 'center'
                                }}
                            >
                                {INCORRECT_CREDENTIALS_ERROR}
                            </Typography>
                        }

                        <Box
                            sx={{
                                display: 'flex',
                                flexDirection: 'row',
                            }}
                        >
                            <Button 
                                variant="outlined"
                                color="error"
                                sx={{
                                    my: 2,
                                    flexGrow: 1,
                                }}
                            >
                                Cancel
                            </Button>
                            
                            <Box
                                sx={{
                                    width: '30px'
                                }}
                            />

                            <Button 
                                variant="outlined"
                                sx={{
                                    my: 2,
                                    flexGrow: 1,
                                }}
                                onClick={this.login}
                                disabled={this.state.usernameError || this.state.passwordError}
                            >
                                Log in
                            </Button>
                        </Box>
                    </Box>
                </Box>
            </Modal>
        )
    }
}

export default LoginDialog