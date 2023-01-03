import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';

export default function MainAppBar() {

    return (
        <AppBar 
            position="static" 
            sx={{ 
                m: 0,
                px: 5,
            }}
        >
            <Toolbar
                sx={{
                    display: 'flex',
                    justifyContent: 'space-between'
                }}
            >
                
                {/* Title */}
                <Typography
                    variant="h5"
                >
                    My Projects
                </Typography>

                {/* Actions */}
                <Button 
                    variant="contained"
                    disableElevation
                    disableFocusRipple
                    disableRipple
                    sx={{
                        "&:hover": {backgroundColor: "transparent", }
                    }}
                >
                <Typography
                    variant="h5"
                >
                    Log in
                </Typography>
                </Button>
            </Toolbar>
        </AppBar>
    );
}