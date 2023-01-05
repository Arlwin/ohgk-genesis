import { useState, useEffect } from 'react'
import { getProjectsFromLocal, setProjectsToLocal } from '../services/ProjectService'

import Box from '@mui/material/Box';
import Snackbar from '@mui/material/Snackbar';

import NewProjectDialog from '../components/NewProjectDialog';

import HttpService from '../services/HttpService';
import Project from '../models/Project';
import ProjectCard from '../components/ProjectCard';
import ConfirmDialog from '../components/ConfirmDialog';

export default function HomePage() {

    const [projects, setProjects] = useState(getProjectsFromLocal());
    const [deleteProjectDialog, setDeleteProjectDialog] = useState({
        open: false,
        message: '',
        projectId: null,
        action: 'Delete',
    });
    const [addProjectDialog, setAddProjectDialog] = useState(false);
    const [snackbar, setSnackbar] = useState({
        open: false,
        message: '',
    })

    const [projectStatuses, setProjectStatuses] = useState([]);
    useEffect(
        () => {

        HttpService.get('/projects/status')
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
                        );
                    }

                    setProjectStatuses(statuses);
                } 
            );
        }, 
        []
    )

    const [projectTypes, setProjectTypes] = useState([]);
    const [projectTypesMap, setProjectTypesMap] = useState({});
    useEffect(
        () => {
            HttpService.get('/projects/types')
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

                    setProjectTypes(types);
                    setProjectTypesMap(data.data);
                } 
            )
        }, 
        []
    )
    
    const [editProjectDialog, setEditProjectDialog] = useState(false);
    const [editProject, setEditProject] = useState(null);

    const createProject = (project) => {
        
        const nProjects = [...projects, project];
        setProjects(nProjects);
        setAddProjectDialog(false);

        setProjectsToLocal(nProjects);

        setSnackbar({
            open: true,
            message: 'Project Successfully Created'
        });
    };

    const updateProject = (project) => {

        const nProjects = projects.map((proj, i) => {

            if (i === project.id) return project;
            else return proj;
        });

        setProjects(nProjects);
        setEditProjectDialog(false);

        setProjectsToLocal(nProjects);

        setSnackbar({
            open: true,
            message: 'Project Successfully Updated'
        });
    }

    const handleUpdateProject = (index) => {
        
        let nProject = projects[index];
        nProject.id = index;

        setEditProjectDialog(true);
        setEditProject(nProject);
    }

    const handleDeleteProject = (index) => {

        setDeleteProjectDialog(
            (deleteProjectDialog) => ({
                ...deleteProjectDialog,
                open: true,
                message: `Delete Project ${projects[index].name}`,
                projectId: index,
            })
        );
    }

    const deleteProject = (projectId) => {

        const nProjects = projects.filter((project, i) => i !== deleteProjectDialog.projectId);

        setProjects(nProjects);
        setDeleteProjectDialog(deleteProjectDialog => ({
            ...deleteProjectDialog,
            open: false,
            message: '',
            projectId: null,
        }));
        
        setProjectsToLocal(nProjects);

        setSnackbar({
            open: true,
            message: 'Project Successfully Deleted'
        });
    }
    
    return (
        <Box
            sx={{
                py: 2,
                display: 'flex',
                flexWrap: 'wrap',
            }}
        >   
            {/* Dialogs */}
            <NewProjectDialog
                key = "newProjectDialog"
                open = { addProjectDialog }
                handleClose = { () => { setAddProjectDialog(false) } }
                submitProject = { createProject }
                statuses = { projectStatuses }
                types = { projectTypes }
            />

            <NewProjectDialog
                key = "editProjectDialog"
                open = { editProjectDialog }
                handleClose = { () => { setEditProjectDialog(false) } }
                submitProject = { updateProject }
                statuses = { projectStatuses }
                types = { projectTypes }
                project = { editProject }
                edit
            />

            <ConfirmDialog 
                open = { deleteProjectDialog.open }
                handleClose = { () => { setDeleteProjectDialog(deleteProjectDialog => ({...deleteProjectDialog, open: false })) }}
                title = { deleteProjectDialog.message }
                message = 'Are you sure you want to delete this project?'
                action = { deleteProjectDialog.action }
                confirm = { () => deleteProject(deleteProjectDialog.projectId) }
            />

            {/* Snackbars */}
            <Snackbar 
                open = { snackbar.open }
                autoHideDuration = { 5000 }
                onClose = {() => { setSnackbar(snackbar => ({...snackbar, open: false})) }}
                message = { snackbar.message }
            />

            <ProjectCard 
                empty
                key = { -2 }
                onClick={ () => { setAddProjectDialog(true) } }
            />

            <ProjectCard 
                key = { -1 }
                project = { createSampleProject() } 
                types = { projectTypesMap }
                disableButtons
            />

            { 
                projects.map(
                    (project, i) => {

                        let nProject = {...project};

                        nProject.type = projectTypesMap[project.type]; 

                        return (
                            <ProjectCard
                                key = { i }
                                project = { nProject } 
                                types = { projectTypesMap }
                                openEdit = { () => { handleUpdateProject(i) } }
                                openDelete = { () => { handleDeleteProject(i) } }
                            />
                        );
                    }
                )
            }
        </Box>
    )
}

function createSampleProject() {

    let project = {
        id: -1,
        name: 'Sample Project',
        description: 'This is a sample project. This is how a Project looks like here. You can add more by clicking the + button on the left.',
        languages: ['JAVA', 'PYTHON'],
        type: 'Web Application',
        status: 'DONE',
        url: 'https://sample-project.com',
    }

    return Project.fromObject(project);
}