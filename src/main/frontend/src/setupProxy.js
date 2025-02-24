const { createProxyMiddleware } = require("http-proxy-middleware")

const isBackendRunning = async () => {
    try {
        const response = await fetch("http://localhost:8080");
        return response.ok;
    } catch (error) {
        return false;
    }
};

module.exports = async function(app) {
    const backendAvailable = await isBackendRunning();

    if (backendAvailable) {
        app.use(
            '/',
            createProxyMiddleware({
                target: 'http://localhost:8080',
                changeOrigin: true,
            })
        );
        console.log("backend is running");
    } else {
        console.log("backend is not running");
    }
}