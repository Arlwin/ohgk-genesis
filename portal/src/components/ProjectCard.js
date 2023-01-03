import { useState } from 'react'

import { getStatusColor } from '../theme/utils';

import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Chip from '@mui/material/Chip';
import Link from '@mui/material/Link';
import Card from '@mui/material/Card';
import IconButton from '@mui/material/IconButton';

import AddIcon from '@mui/icons-material/Add';
import LinkIcon from '@mui/icons-material/Link';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';

export default function ProjectCard(props) {

    useState()
    
    const empty = (props.empty === undefined ? false : props.empty);

    if (empty) {

        const color = 'rgb(0, 0, 0, .2)';

        return (
            <Card
                sx={{
                    my: 2,
                    mx: 2.5,
                    p: 2,
                    width: '20%',
                    borderRadius: '10px',
                    border: `2px dashed ${color}`, //? can we add to theme?
                    backgroundColor: color,
                    color: 'rgb(0, 0, 0, .4)',
                    display: 'flex',
                    justifyContent: 'center',
                    cursor: 'pointer',
                }}
                onClick={ props.onClick }
            >
                <AddIcon 
                    sx={{
                        fontSize: '60px',
                        alignSelf: 'center',
                    }} 
                />
            </Card>
        );
    }
    else {

        return (

            <Card
                sx={{
                    my: 2,
                    mx: 2.5,
                    p: 2,
                    width: '20%',
                    borderRadius: '10px',
                    borderRight: `6px solid ${getStatusColor(props.project.status)}`,
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'space-between'
                }}
            >

                <Box
                    sx={{
                        display: 'flex',
                        justifyContent: 'space-between'
                    }}
                >
                    {/* Project Name */}
                    <Typography
                        variant='h5'
                        sx={{
                            fontWeight: '600',
                            alignSelf: 'center'
                        }}
                    >
                        { props.project.name }
                    </Typography>

                    {/* Actions */}
                    <Box 
                        sx={{
                            display: 'flex',
                            justifyContent: 'flex-end',
                        }}
                    >
                        <IconButton 
                            onClick={ props.openEdit }
                            disabled={ props.disableButtons ?? false }
                        >
                            <EditOutlinedIcon fontSize='small' />
                        </IconButton>
                        <IconButton 
                            onClick={ props.openDelete }
                            disabled={ props.disableButtons ?? false }
                        >
                            <DeleteOutlineIcon fontSize='small' />
                        </IconButton>
                    </Box>
                </Box>
                

                <Box 
                    sx={{
                        height: '25px'
                    }}
                />

                {/* Project Description */}
                <Typography
                    variant='body1'
                >
                    { props.project.description }
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
                    { buildLanguagesEl(props.project.languages) }
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
                    { props.project.type }
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
                            href={ props.project.url }
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
                            { props.project.url }
                        </Link> 
                </Box>
                
                <Box 
                    sx={{
                        height: '5px'
                    }}
                />
            </Card>
        );
    } 
}

function buildLanguagesEl(languages) {

    var languagesEl = [];

    languages.forEach( 
        (language) => {
            languagesEl.push(
                <Chip 
                    key={language}
                    label={language.toUpperCase()} 
                    // color="primary" //! Change this in theme
                    variant='outlined'
                    size="small"
                    sx={{
                        mr: 1,
                        fontWeight: '600',
                        color: 'secondary.main',
                        borderColor: 'secondary.main'
                    }}
                />
            )
        }
    );

    return languagesEl;
}