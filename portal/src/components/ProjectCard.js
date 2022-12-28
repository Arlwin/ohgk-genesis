import React from 'react'

import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Chip from '@mui/material/Chip';
import Link from '@mui/material/Link';

import LinkIcon from '@mui/icons-material/Link';

export class ProjectCard extends React.Component {

    project;

    constructor(props) {
        super(props);

        this.project = props.project;
    }

    getStatusColor() {

        // ! Temporary. For prototype
        /**
         *     TODO,
         *     IN_PROGRESS,
         *     DONE,
         */
        switch(this.project.status) {

            case 'TODO': return '#52B2CF';
            case 'IN_PROGRESS': return '#FB4D3D';
            case 'DONE': return '#59CD90';
            default: return '#52B2CF';
        }
    }

    buildLanguagesEl() {

        const languages = this.project.languages;
        var languagesEl = [];

        for (var i in languages) {
            languagesEl.push(
                <Chip 
                    key={languages[i]}
                    label={languages[i].toString().toUpperCase()} 
                    color="primary" //! Change this in theme
                    variant='outlined'
                    size="small"
                    sx={{
                        mr: 1,
                    }}
                />
            )
        }

        return languagesEl;
    }

    render() {
        return (
            <Box
                sx={{
                    my: 2,
                    mx: 2.5,
                    p: 2,
                    width: '20%',
                    borderRadius: '10px',
                    borderRight: '6px solid ' + this.getStatusColor(),
                    display: 'flex',
                    flexDirection: 'column',
                    backgroundColor: '#EBEBEB', //! Change in themes
                }}
            >   

                {/* Project Name */}
                <Typography
                    variant='h5'
                    sx={{
                        fontWeight: '600'
                    }}
                >
                    {this.project.name}
                </Typography>

                <Box 
                    sx={{
                        height: '10px'
                    }}
                />

                {/* Project Description */}
                <Typography
                    variant='body1'
                >
                    {this.project.description}
                </Typography>

                <Box 
                    sx={{
                        height: '30px'
                    }}
                />

                {/* Project Languages */}
                <Box
                    sx={{
                        display: 'flex',
                        flexWrap: 'wrap',
                    }}
                >
                    {this.buildLanguagesEl()}
                </Box>

                <Box 
                    sx={{
                        height: '5px'
                    }}
                />

                {/* Project Type */}
                <Typography
                    variant='overline'
                    sx={{
                        fontWeight: '600'
                    }}
                >
                    { this.project.type }
                </Typography>

                <Box 
                    sx={{
                        height: '10px'
                    }}
                />

                {/* Project Url */}
                <Box
                    sx={{
                        display: 'flex',
                    }}
                >
                    <LinkIcon 
                        sx={{
                            mr: 2
                        }}
                    />
                        <Link
                            href={ this.project.url }
                            underline="hover"
                            variant='caption'
                            sx={{
                                alignSelf: 'center',
                                fontSize: '12px',
                                fontStyle: 'italic',
                                pt: .2, // bruh
                            }}
                            target="_blank"
                            rel="noopener"
                        >
                            { this.project.url }
                        </Link> 
                </Box>
                
                <Box 
                    sx={{
                        height: '5px'
                    }}
                />
            </Box>
        );
    }
}

export default ProjectCard