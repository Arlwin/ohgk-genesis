import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography'
import Button from '@mui/material/Button';

export default function ConfirmDialog(props) {

    return (
        <Modal
            open = {props.open}
            onClose = {props.handleClose}
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
                    p: 1,
                }}
            >
                <Box
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        p: 2,
                    }}
                >
                    <Typography 
                        variant="h4"
                        sx={{
                            fontWeight: '600',
                            alignSelf: 'center'
                        }}
                    >
                        { props.title }
                    </Typography>

                    <Box height={20} />

                    <Typography 
                        variant="body1"
                        sx={{
                            alignSelf: 'center'
                        }}
                    >
                        { props.message }
                    </Typography>
                    
                    <Box height={20} />
                </Box>
                <Box
                    sx={{
                        display: 'flex'
                    }}
                >
                    <Button
                        fullWidth
                        variant='contained'
                        onClick={ props.handleClose }
                    >
                        Cancel
                    </Button>
                    <Box width={ 20 }/>
                    <Button
                        fullWidth
                        variant='contained'
                        onClick={ props.confirm }
                    >
                        { props.action }
                    </Button>
                </Box>
            </Box>
        </Modal>
    )
}