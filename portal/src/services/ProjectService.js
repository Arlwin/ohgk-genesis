export function getProjectsFromLocal() {

    const projectsKey = 'projects';
    const projects = localStorage.getItem(projectsKey);

    if (projects === null) return [];    

    return JSON.parse(projects);
}

export function setProjectsToLocal(projects) {

    const projectsKey = 'projects';
    localStorage.setItem(projectsKey, JSON.stringify(projects));
}