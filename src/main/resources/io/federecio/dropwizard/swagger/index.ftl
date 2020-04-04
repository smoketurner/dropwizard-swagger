<#-- @ftlvariable name="" type="io.federecio.dropwizard.swagger.SwaggerView" -->
<!-- HTML for static distribution bundle build -->
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Swagger UI</title>
    <link rel="stylesheet" type="text/css" href="${swaggerAssetsPath}/swagger-ui.css" >
    <link rel="icon" type="image/png" href="${swaggerAssetsPath}/favicon-32x32.png" sizes="32x32" />
    <link rel="icon" type="image/png" href="${swaggerAssetsPath}/favicon-16x16.png" sizes="16x16" />
    <style>
      html
      {
        box-sizing: border-box;
        overflow: -moz-scrollbars-vertical;
        overflow-y: scroll;
      }

      *,
      *:before,
      *:after
      {
        box-sizing: inherit;
      }

      body
      {
        margin:0;
        background: #fafafa;
      }
    </style>
  </head>

  <body>
    <div id="swagger-ui"></div>
    <#if customJavascriptPath ??>
    <script src="${customJavascriptPath}"> </script>
    </#if>
    <script src="${swaggerAssetsPath}/swagger-ui-bundle.js"> </script>
    <script src="${swaggerAssetsPath}/swagger-ui-standalone-preset.js"> </script>
    <script>
    window.onload = function() {

      // Begin Swagger UI call region
      const ui = SwaggerUIBundle({
        url: "${contextPath}/swagger.json",
        <#if validatorUrl??>
        validatorUrl: "${validatorUrl}",
        <#else>
        validatorUrl: null,
        </#if>
        dom_id: "#swagger-ui",
        deepLinking: true,
        supportedSubmitMethods: ["get", "post", "put", "delete", "patch"],
        docExpansion: "none",
        jsonEditor: false,
        tagsSorter: "alpha",
        operationsSorter: "alpha",
        defaultModelRendering: "schema",
        showRequestHeaders: false,
        presets: [
          SwaggerUIBundle.presets.apis,
          SwaggerUIStandalonePreset
        ],
        plugins: [
          SwaggerUIBundle.plugins.DownloadUrl
        ],
        oauth2RedirectUrl: window.location.protocol + "//" + window.location.host + "${contextPath}/oauth2-redirect.html",
        layout: "StandaloneLayout",
        requestInterceptor: (typeof customRequestInterceptor == 'function' ? customRequestInterceptor : null)
      });

      ui.initOAuth({
        clientId: "${oauth2Configuration.clientId!"your-client-id"}",
        clientSecret: "${oauth2Configuration.clientSecret!"your-client-secret-if-required"}",
        realm: "${oauth2Configuration.realm!"your-realms"}",
        appName: "${oauth2Configuration.appName!"your-app-name"}",
        scopeSeparator: "${oauth2Configuration.scopeSeparator!" "}",
        additionalQueryStringParams: {
        <#list oauth2Configuration.additionalQueryStringParams?keys as additionalQueryStringParamKey>
          "${additionalQueryStringParamKey}": "${oauth2Configuration.additionalQueryStringParams[additionalQueryStringParamKey]}"
        </#list>
        }
      })
      // End Swagger UI call region

      window.ui = ui
    }
  </script>
  </body>
</html>
