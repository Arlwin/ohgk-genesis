class Project {

    id = null;
    name = '';
    description = '';
    status = '';
    type = '';
    languages = [];
    url = '';

    constructor(
        id,
        name,
        description,
        languages,
        type,
        status,
        url,
    ) {
        
        this.id = id;
        this.name = name;
        this.description = description;
        this.languages = Array.from(languages);
        this.type = type;
        this.status = status;
        this.url = url;
    }

    static fromObject(project) {

        return new Project(
            project.id,
            project.name,
            project.description,
            project.languages,
            project.type,
            project.status,
            project.url
        );
    }
}

export default Project;