import React from 'react'

import httpService from '../services/httpService';

import Box from '@mui/material/Box';

import LoginDialog from '../components/LoginDialog';
import ProjectCard from '../components/ProjectCard';

export class HomePage extends React.Component {

    constructor(props) {

        super(props);

        this.state = {
            login: props.login === undefined ? false : props.login,
            projects: []
        }

        this.closeLogin = this.closeLogin.bind(this);
    }

    closeLogin() {

        this.setState({
            login: false
        });
    }

    fetchProjects() {

        httpService.get('/projects')
            .then(
                (response) => {

                    const data = response.data;

                    this.setState({
                        projects: data.data
                    })
                }
            )
    }

    componentDidMount() {
        this.fetchProjects();
    }

    buildProjectsElement() {

        const projectsEl = []
        const projects = this.state.projects;

        for (var i in projects) {
            
            projectsEl.push(
                <ProjectCard
                    key={projects[i].id}
                    project = {projects[i]} 
                />
            )
        }

        return projectsEl;
    }

    render() {
        return (
            <Box>
                <LoginDialog 
                    open = { this.state.login }
                    handleClose = { this.closeLogin }
                />

                <Box
                    sx={{
                        p: 2,
                        display: 'flex',
                        flexWrap: 'wrap',
                    }}
                >
                    { this.buildProjectsElement() }
                </Box>
            </Box>

        )
    }
}

export default HomePage