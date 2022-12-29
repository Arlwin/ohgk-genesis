import React from 'react'

import Box from '@mui/material/Box';

import ProjectCard from '../components/ProjectCard';
import NewProjectDialog from '../components/NewProjectDialog';

import httpService from '../services/httpService';

export class HomePage extends React.Component {

    constructor(props) {

        super(props);
        
        this.state = {
            projects: [],
            addProjectDialog: false,
            projectStatuses: [],
            projectTypes: [],
            projectStatusesMap: {},
            projectTypesMap: {},
        }

        this.createNewProject = this.createNewProject.bind(this);
    }

    buildProjectsElement() {

        const projectsEl = []
        const projects = this.state.projects;

        for (var i in projects) {
            
            projectsEl.push(
                <ProjectCard
                    key = {i}
                    project = {projects[i]} 
                    types = { this.state.projectTypesMap }
                />
            )
        }

        return projectsEl;
    }

    createNewProject(project) {

        const projects = this.state.projects;

        // special case for type
        project.type = this.state.projectTypesMap[project.type]

        projects.push(project);

        this.setState({
            projects: projects,
            addProjectDialog: false,
        });
    }

    fetchProjectTypes() {

        httpService.get('/projects/types')
            .then(
                (response) => {

                    const data = response.data;
                    const keys = Object.keys(data.data);

                    var types = [];

                    for (var i in keys) {

                        types.push(
                            {
                                name: keys[i],
                                value: data.data[keys[i]]
                            }
                        )
                    }

                    this.setState({
                        projectTypes: types,
                        projectTypesMap: data.data,
                    });
                } 
            );
    }

    fetchProjectStatuses() {

        httpService.get('/projects/status')
            .then(
                (response) => {

                    const data = response.data;
                    const keys = Object.keys(data.data);

                    var statuses = [];

                    for (var i in keys) {

                        statuses.push(
                            {
                                name: keys[i],
                                value: data.data[keys[i]]
                            }
                        )
                    }

                    this.setState({
                        projectStatuses: statuses,
                        projectStatusesMap: data.data,
                    });
                } 
            );
    }

    componentDidMount(){

        this.fetchProjectTypes();
        this.fetchProjectStatuses();
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
                <NewProjectDialog
                    key = "newProjectDialog"
                    open = { this.state.addProjectDialog }
                    handleClose = { () => { this.setState({ addProjectDialog: false }) } }
                    createProject = { this.createNewProject }
                    statuses = { this.state.projectStatuses }
                    types = { this.state.projectTypes }
                />

                <ProjectCard 
                    empty
                    onClick={ () => { this.setState({ addProjectDialog: true }) } }
                />
                { this.buildProjectsElement() }
            </Box>
        )
    }
}

export default HomePage