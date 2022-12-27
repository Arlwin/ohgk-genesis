import React from 'react'

import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

export class ProjectCard extends React.Component {

    project;

    constructor(props) {
        super(props);

        this.project = props.project;
    }

    render() {
        return (
            <Box>
                <Typography
                    variant='h3'
                >
                    {this.project.name}
                </Typography>
            </Box>
        );
    }
}

export default ProjectCard