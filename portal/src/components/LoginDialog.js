import React from 'react'

import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

export class LoginDialog extends React.Component {

    textFieldCss = {
        my: 2,
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
                            label="Username" 
                            variant="outlined"
                            sx={this.textFieldCss} 
                        />
                        <TextField 
                            label="Password" 
                            variant="outlined" 
                            sx={this.textFieldCss}
                            type='password'
                        />

                        <Button 
                            variant="outlined"
                            sx={this.textFieldCss}
                        >Log in</Button>
                    </Box>
                </Box>
            </Modal>
        )
    }
}

export default LoginDialog