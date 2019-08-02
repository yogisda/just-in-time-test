export const environment = {
  production: true,

  backendApi: {
    url: "http://localhost:1337",
    basePath: "/",
    paths: {
      global: "global",
      itemStatus: "itemstatus",
      role: "role",
      schedule: "schedule",
      scheduleStatus: "schedulestatus",
      scheduleItem: "scheduleitem",
      user: "user"
    },
    authPath: "/account",
    authPaths: {
      login: "/login",
      logout: "/logout"
    }
  }
};
