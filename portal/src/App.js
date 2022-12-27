import React from 'react'

import { RouterProvider } from 'react-router-dom';
import router from './router/routes'

export class App extends React.Component {

    render() {
        return (
            <RouterProvider router={router} /> 
        );
    }
}

export default App;
