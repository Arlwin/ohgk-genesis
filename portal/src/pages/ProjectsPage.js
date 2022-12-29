import React from 'react'

import httpService from '../services/httpService';

import Box from '@mui/material/Box';

import ProjectCard from '../components/ProjectCard';

export class ProjectsPage extends React.Component {

    user;

    constructor(props) {

        super(props);

        this.state = {
            projects: []
        }

        this.user = this.props.user === undefined ? null : this.props.user;
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

        // if (this.user !== null) this.fetchProjects();
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
            <Box
                sx={{
                    py: 2,
                    display: 'flex',
                    flexWrap: 'wrap',
                }}
            >
                { this.buildProjectsElement() }
            </Box>
        )
    }
}

export default ProjectsPage