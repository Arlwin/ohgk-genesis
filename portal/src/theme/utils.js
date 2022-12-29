export const getStatusColor = (status) => {

    switch(status) {

        case 'TODO': return '#52B2CF';
        case 'IN_PROGRESS': return '#FB4D3D';
        case 'DONE': return '#59CD90';
        default: return '#52B2CF';
    }
}

