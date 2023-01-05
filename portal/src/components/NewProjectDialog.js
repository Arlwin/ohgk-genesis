import { useState, useEffect }  from 'react'

import Project from '../models/Project'

import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography'
import TextField from '@mui/material/TextField';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import Button from '@mui/material/Button';
import InputAdornment from '@mui/material/InputAdornment';
import Chip from '@mui/material/Chip';

import LinkIcon from '@mui/icons-material/Link';

//! TODO: Add Validation

export default function NewProjectDialog(props) {

    const initState = {
        id: null,
        name: '',
        description: '',
        languages: new Set(),
        url: '',
    }

    const [project, setProject] = useState(initState);
    const [form, setForm] = useState({
        language: '',
        types: [],
        statuses: []
    });
    const [validations, setValidations] = useState({
        name: null,
        description: null,
        languages: null,
        valid: false,
    });

    // Validations
    useEffect(
        () => {

            let nValidations = {
                name: null,
                description: null,
                languages: null,
            };
            let valid = true;

            // Name
            if (project.name.trim() === '') {

                nValidations['name'] = 'Name cannot be empty';
                valid = false;
            } 

            // Description
            if (project.description.trim() === '') {

                nValidations['description'] = 'Description cannot be empty';
                valid = false;
            } 

            // Languages
            if (project.languages.size <= 0) {

                nValidations['languages'] = 'Must have at least one language';
                valid = false;
            } 

            nValidations['valid'] = valid;
            setValidations(nValidations);
        },
        [project]
    );

    // Type
    useEffect(
        () => {

            if (props.types && props.types.length > 0) {

                setForm(form => ({
                    ...form, 
                    types: props.types,
                }));

                setProject(project => ({
                    ...project, 
                    type: props.types[0].name,
                }));
            }
        },
        [props.types]
    );

    // Status
    useEffect(
        () => {

            if (props.statuses && props.statuses.length > 0) {

                setForm(form => ({
                    ...form, 
                    statuses: props.statuses,
                }));

                setProject(project => ({ 
                    ...project,
                    status: props.statuses[0].name,
                }));
            }
        },
        [props.statuses]
    );

    // Existing Project
    useEffect(
        () => {

            if (props.edit) {

                if (props.project && props.project.id !== undefined) {

                    setProject(project => ({
                        ...project,
                        ...props.project
                    }));
                }
            }
        },
        [props.project, props.edit]
    )

    const handleInput = (event) => {
        
        const target = event.target;
        const name = target.name;
        const value = target.value;

        setProject(project => ({ ...project, [name]: value }));
    }

    const handleLanguageRemove = (language) => {

        let languages = new Set([...project.languages]);

        languages.delete(language.toUpperCase());

        setProject(project => ({ ...project, languages: languages }));
    }

    const handleLanguageAdd = (event) => {

        if (event.key === 'Enter') {

            let languages = new Set([...project.languages]);

            languages.add(form.language.toString().trim().toUpperCase());

            setProject(project => ({ ...project, languages: languages}));
            setForm(form => ({ ...form, language: ''}));
        }
    }

    const submit = () => {

        let projectObj = new Project (
            project.id ?? null,
            project.name,
            project.description,
            project.languages,
            project.type,
            project.status,
            project.url
        );

        props.submitProject(projectObj);

        if (!props.edit) {

            setProject(project => ({
                ...project,
                ...initState,
            }));

            // this.setState({ //? Special case. Not updating set if it wasnt for this
            //     languages: new Set()
            // });
        }
    }

    const title = props.edit !== undefined
        ? 'Update Project'
        : 'Create Project';

    const submitLabel = props.edit !== undefined
        ? 'Update'
        : 'Create';

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
                    width: 600,
                    bgcolor: 'background.paper',
                    borderRadius: '5px',
                    outline: 0,
                    boxShadow: 12,
                    px: 4,
                    py: 3,
                }}
            >
                <Box
                    sx={{
                        pb: 4,
                        display: 'flex',
                        flexDirection: 'column'
                    }}
                >
                    {/* Title */}
                    <Typography 
                        variant="h5"
                        sx={{
                            fontWeight: 600,
                            alignSelf: 'center'
                        }}
                    >
                        { title }
                    </Typography>

                    <Box sx={{ height: '40px' }} />

                    {/* Project Name */}
                    <TextField
                        required
                        name='name'
                        label="Project Name"
                        value={ project.name }
                        onChange={ handleInput }
                        error= { validations.name !== null }
                        helperText={ validations.name }
                    />

                    <Box sx={{ height: '30px' }} />

                    {/* Project Description */}
                    <TextField
                        required
                        name='description'
                        multiline
                        label="Project Description"
                        minRows={4}
                        maxRows={6}
                        value={ project.description }
                        onChange={ handleInput }
                        error= { validations.description !== null }
                        helperText={ validations.description }
                    />

                    <Box sx={{ height: '30px' }} />

                    {/* Project Languages. Consider making a list for this and change to autocomplete*/}
                    <TextField
                        required
                        name='language'
                        label="Languages"
                        value={ form.language }
                        onChange={ (event) => { setForm(form => ({ ...form, language: event.target.value })) } }
                        onKeyDown={ handleLanguageAdd }
                        InputProps={{
                            startAdornment: [...project.languages].map(
                                    (language) => {
                                        return (
                                            <Chip
                                                key={language}
                                                label={language}
                                                onDelete={() => handleLanguageRemove(language)}
                                                sx={{
                                                    mr: 1,
                                                    borderRadius: '10px'
                                                }}
                                            />
                                        )
                                    }
                            )
                        }}
                        error= { validations.languages !== null }
                        helperText={ validations.languages }
                    />

                    <Box sx={{ height: '30px' }} />

                    {/* Type */}
                    <FormControl>
                        <InputLabel id='type-label'>Type</InputLabel>
                        <Select
                            required
                            name='type'
                            labelId='type-label'
                            label="Type"
                            value={ project.type }
                            onChange={ handleInput }
                        >
                            { form.types.map(
                                (type, i) => {
                                    return (
                                        <MenuItem key={i} value={type.name}>{ type.value }</MenuItem>
                                    )
                                }
                            ) }
                        </Select>
                    </FormControl>

                    <Box sx={{ height: '20px' }} />
                    
                    <Typography
                        variant='overline'
                        sx={{
                            fontSize: '14px'
                        }}
                    >
                        Status
                    </Typography>
                    <ToggleButtonGroup
                        exclusive
                        name='status'
                        value={ project.status }
                        fullWidth
                        onChange={ (event) => { setProject({ ...project, status: event.target.value }); } }
                    >
                        { form.statuses.map(
                            (status, i) => {
                                return (
                                    <ToggleButton key={i} value={status.name}>{ status.value }</ToggleButton>
                                )
                            }
                        ) }
                    </ToggleButtonGroup>

                    <Box sx={{ height: '30px' }} />

                    {/* Project URL */}
                    <TextField
                        required
                        name='url'
                        label="Project URL"
                        value={ project.url }
                        onChange={ handleInput }
                        InputProps={{
                            startAdornment: (
                                <InputAdornment position="start">
                                    <LinkIcon />
                                </InputAdornment>
                            ),
                        }}
                    />
                </Box>

                <Box
                    sx={{
                        display: 'flex',
                        justifyContent: 'space-between',
                    }}
                >
                    <Button
                        variant="contained"
                        fullWidth
                        onClick={ props.handleClose }
                    >
                        Cancel
                    </Button>

                    <Box width="25px"/>
                    
                    <Button
                        variant="contained"
                        fullWidth
                        onClick={ submit }
                        disabled={ !validations.valid }
                    >
                        { submitLabel }
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
}