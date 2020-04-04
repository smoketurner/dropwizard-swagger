(function () {
    $(function () {

    function requestInterceptor(request) {
        request.headers = req.headers || {}
        request.headers["x-custom-header"] = "test-value"
        return new Promise((resolve, reject) => resolve(request));
    }
})();
