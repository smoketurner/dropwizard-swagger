function customRequestInterceptor(request) {
    request.headers["x-custom-header"] = "test"
    return request;
}
