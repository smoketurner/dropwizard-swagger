<#-- @ftlvariable name="" type="com.federecio.dropwizard.swagger.SwaggerView" -->
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${title}</title>
  <link rel="icon" type="image/png" href="${swaggerAssetsPath}/images/favicon-32x32.png" sizes="32x32" />
  <link rel="icon" type="image/png" href="${swaggerAssetsPath}/images/favicon-16x16.png" sizes="16x16" />
  <link href='${swaggerAssetsPath}/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${swaggerAssetsPath}/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${swaggerAssetsPath}/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${swaggerAssetsPath}/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
  <link href='${swaggerAssetsPath}/css/print.css' media='print' rel='stylesheet' type='text/css'/>
  <script src='${swaggerAssetsPath}/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/jquery.slideto.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/jquery.wiggle.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/handlebars-2.0.0.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/underscore-min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/backbone-min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/swagger-ui.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/highlight.7.3.pack.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/jsoneditor.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/marked.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/swagger-oauth.js' type='text/javascript'></script>

  <!-- Some basic translations -->
  <!-- <script src='${swaggerAssetsPath}/lang/translator.js' type='text/javascript'></script> -->
  <!-- <script src='${swaggerAssetsPath}/lang/ru.js' type='text/javascript'></script> -->
  <!-- <script src='${swaggerAssetsPath}/lang/en.js' type='text/javascript'></script> -->

  <script type="text/javascript">
    $(function () {
      // Pre load translate...
      if(window.SwaggerTranslator) {
        window.SwaggerTranslator.translate();
      }
      window.swaggerUi = new SwaggerUi({
        url: "${contextPath}/swagger.json",
        <#if validatorUrl??>
        validatorUrl: "${validatorUrl}",
        <#else>
        validatorUrl: null,
        </#if>
        dom_id: "swagger-ui-container",
        supportedSubmitMethods: ['get', 'post', 'put', 'delete', 'patch'],
        onComplete: function(swaggerApi, swaggerUi){
          if(typeof initOAuth == "function") {
            /*
            initOAuth({
              clientId: "your-client-id",
              clientSecret: "your-client-secret-if-required",
              realm: "your-realms",
              appName: "your-app-name", 
              scopeSeparator: ",",
              additionalQueryStringParams: {}
            });
            */
          }

          if(window.SwaggerTranslator) {
            window.SwaggerTranslator.translate();
          }

          $('pre code').each(function(i, e) {
            hljs.highlightBlock(e)
          });
        },
        onFailure: function(data) {
          log("Unable to Load SwaggerUI");
        },
        docExpansion: "none",
        jsonEditor: false,
        apisSorter: "alpha",
        defaultModelRendering: 'schema',
        showRequestHeaders: false
      });

      <#if showAuth>
        function addApiKeyAuthorization() {
          var key = encodeURIComponent($('#input_apiKey')[0].value);
          if(key && key.trim() != "") {
              var apiKeyAuth = new SwaggerClient.ApiKeyAuthorization("api_key", key, "query");
              window.swaggerUi.api.clientAuthorizations.add("api_key", apiKeyAuth);
              log("added key " + key);
          }
        }

        function addAuthorizationHeader() {
          var key = $('#input_authHeader')[0].value;
          if(key && key.trim() != "") {
              var headerAuth = new SwaggerClient.ApiKeyAuthorization("Authorization", key, "header");
              window.swaggerUi.api.clientAuthorizations.add("Custom Authorization", headerAuth);
              log("added key " + key);
          }
        }

        $('#input_apiKey').change(addApiKeyAuthorization);
        $('#input_authHeader').change(addAuthorizationHeader);
        $('#input_headerSelect').change(function() {
          var toShow = $( this ).val();
          $('#header_'+toShow).show();
          var toHide = (Number(toShow)+1)%2;
          $('#header_'+toHide).hide();
        });

        // if you have an apiKey you would like to pre-populate on the page for demonstration purposes...
        /*
          var apiKey = "myApiKeyXXXX123456789";
          $('#input_apiKey').val(apiKey);
          addApiKeyAuthorization();
        */
      <#else>
        $('#input_apiKey').hide();
        $('#input_authHeader').hide();
        $('#input_headerSelect').hide();
      </#if>

      <#if !showApiSelector>
        $('#explore').hide();
        $('#input_baseUrl').hide();
      </#if>

      window.swaggerUi.load();

      function log() {
        if ('console' in window) {
          console.log.apply(console, arguments);
        }
      }
  });
  </script>
</head>

<body class="swagger-section">
<div id='header'>
  <div class="swagger-ui-wrap">
    <a id="logo" href="http://dropwizard.io"><img class="logo__img" alt="swagger" height="30" width="30" src="myassets/dropwizard-logo.png" /><span class="logo__title">swagger</span></a>
    <form id='api_selector'>
      <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/></div>
      <div class='input'>
          <select id="input_headerSelect">
              <option value="0">api_key</option>
              <option value="1">Auth Header</option>
          </select>
      </div>
      <div class='input' id="header_0"><input placeholder="api_key" id="input_apiKey" name="apiKey" type="text" /></div>
      <div class='input' id="header_1" style="display: none;"><input placeholder="Basic ..." id="input_authHeader" name="authHeader" type="text"/></div>
      <div class='input'><a id="explore" href="#" data-sw-translate>Explore</a></div>
    </form>
  </div>
</div>

<div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div>
<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
</body>
</html>

