import * as React from "react";
import { fetchUtils, Admin, Resource } from 'react-admin';
import { UserList, UserEdit, UserCreate } from './users';

import jsonServerProvider from 'ra-data-json-server';
import Dashboard from "./Dashboard";
import authProvider from "./authProvider";

const httpClient = (url, options = {}) => {
  if (!options.headers) {
      options.headers = new Headers({ Accept: 'application/json' });
  }
  const { token } = JSON.parse(localStorage.getItem('token'));
   options.headers.set('Authorization', `Bearer ${token}`);
  return fetchUtils.fetchJson(url, options);
};

const dataProvider = jsonServerProvider('http://localhost:8081', httpClient);

const App = () => (
      <Admin dashboard={Dashboard} dataProvider={dataProvider} authProvider={authProvider}>
        {permissions =>[
          permissions === "admin" ? <Resource name="users" list={UserList} edit={UserEdit} 
          create={permissions === "admin" ? UserCreate:null} /> : null, 
        ]}
      </Admin>
  );
export default App;