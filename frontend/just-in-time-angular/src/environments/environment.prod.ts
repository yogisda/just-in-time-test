export const environment = {
    production: true,

    backendApi: {
        url: "http://141.87.68.249:8080/Just-in-Time-REST",
        basePath: "/rest",
        paths: {
            employee: "/employees",
            project: "/projects",
            timebooking: "/timebookings",
            milestone: "/milestones",
            customers: "/customers",
            permission: "/permissions",
            roles: ""
        },
        authPath: "/rest/employees",
        authPaths: {
            login: "/login",
            logout: "/logout"
        }
    }
};

