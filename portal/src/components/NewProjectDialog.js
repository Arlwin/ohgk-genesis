import React  from 'react'

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

export class NewProjectDialog extends React.Component {

    initState = {
        name: '',
        description: '',
        language: '',
        languages: new Set(),
        url: '',
    }

    constructor(props) {
        super(props);

        this.state = this.initState;

        this.handleInput = this.handleInput.bind(this);
        this.handleStatusInput = this.handleStatusInput.bind(this);
        this.createProject = this.createProject.bind(this);
        this.handleLanguageAdd = this.handleLanguageAdd.bind(this);
        this.handleLanguageRemove = this.handleLanguageRemove.bind(this);
    }

    buildSelectType() {

        const types = this.props.types;
        var typesEl = [];

        for (var i in types) {

            typesEl.push(
                <MenuItem key={i} value={types[i].name}>{ types[i].value }</MenuItem>
            );
        }

        return typesEl;
    }

    buildToggleStatus() {

        const statuses = this.props.statuses;
        var statusEl = [];

        for (var i in statuses) {

            statusEl.push(
                <ToggleButton key={i} value={statuses[i].name}>{ statuses[i].value }</ToggleButton>
            );
        }

        return statusEl;
    }

    handleInput(event) {
        
        const target = event.target;
        const name = target.name;
        const value = target.value;

        this.setState({
            [name]: value
        });
    }

    handleStatusInput(event) {
        
        const target = event.target;
        const value = target.value;

        this.setState({
            'status' : value
        });
    }

    createProject() {

        this.props.createProject(
            {
                name: this.state.name,
                description: this.state.description,
                languages: this.state.languages,
                type: this.state.type,
                status: this.state.status,
                url: this.state.url,
            }
        );

        this.setState(this.initState);
        
        this.setState({ //? Special case. Not updating set if it wasnt for this
            languages: new Set()
        });
    }

    handleLanguageAdd(event) {

        if (event.key === 'Enter') {

            let languages = this.state.languages;

            languages.add(this.state.language.toString().trim().toUpperCase());

            this.setState({
                languages: languages,
                language: '',
            });
        }
    }

    handleLanguageRemove(event, lang) {

        let languages = this.state.languages;

        languages.delete(lang.toUpperCase());

        this.setState({
            languages: languages,
        });
    }

    buildLanguageChips() {

        const languages = this.state.languages;
        let chips = [];

        languages.forEach(
            (lang) => {
                chips.push(
                    <Chip
                        key={lang}
                        label={lang}
                        onDelete={(event) => this.handleLanguageRemove(event, lang)}
                        sx={{
                            mr: 1,
                            borderRadius: '10px'
                        }}
                    />
                )
            }
        );

        return chips;
    }

    componentDidUpdate(prevProps) {

        if (prevProps.types !== this.props.types) {

            this.setState({

                type: this.props.types[0].name,
            });
        }

        if (prevProps.statuses !== this.props.statuses) {

            this.setState({

                status: this.props.statuses[0].name,
            });
        }

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
                            NEW PROJECT
                        </Typography>

                        <Box sx={{ height: '40px' }} />

                        {/* Project Name */}
                        <TextField
                            required
                            name='name'
                            label="Project Name"
                            value={ this.state.name }
                            onChange={ this.handleInput }
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
                            value={ this.state.description }
                            onChange={ this.handleInput }
                        />

                        <Box sx={{ height: '30px' }} />

                        {/* Project Languages. Consider making a list for this and change to autocomplete*/}
                        <TextField
                            required
                            name='language'
                            label="Languages"
                            value={ this.state.language }
                            onChange={ this.handleInput }
                            onKeyDown={ this.handleLanguageAdd }
                            InputProps={{
                                startAdornment: this.buildLanguageChips(),
                            }}
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
                                value={ this.state.type }
                                onChange={ this.handleInput }
                            >
                                { this.buildSelectType() }
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
                            value={this.state.status}
                            fullWidth
                            onChange={ this.handleStatusInput }
                        >
                            { this.buildToggleStatus() }
                        </ToggleButtonGroup>

                        <Box sx={{ height: '30px' }} />

                        {/* Project URL */}
                        <TextField
                            required
                            name='url'
                            label="Project URL"
                            value={ this.state.url }
                            onChange={ this.handleInput }
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
                        >
                            Cancel
                        </Button>

                        <Box width="25px"/>
                        
                        <Button
                            variant="contained"
                            fullWidth
                            onClick={ this.createProject }
                        >
                            Create
                        </Button>
                    </Box>
                </Box>
            </Modal>
        )
    }
}

export default NewProjectDialog