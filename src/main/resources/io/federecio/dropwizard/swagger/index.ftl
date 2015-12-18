<#-- @ftlvariable name="" type="com.federecio.dropwizard.swagger.SwaggerView" -->
<!DOCTYPE html>
<html>
<head>
  <title>Swagger UI</title>
  <link href='${swaggerAssetsPath}/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${swaggerAssetsPath}/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${swaggerAssetsPath}/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${swaggerAssetsPath}/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
  <link href='${swaggerAssetsPath}/css/screen.css' media='print' rel='stylesheet' type='text/css'/>
  <script src='${swaggerAssetsPath}/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/jquery.slideto.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/jquery.wiggle.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/handlebars-2.0.0.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/underscore-min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/backbone-min.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/swagger-ui.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/highlight.7.3.pack.js' type='text/javascript'></script>
  <script src='${swaggerAssetsPath}/lib/marked.js' type='text/javascript'></script>
  
    <!-- enabling this will enable oauth2 implicit scope support -->
  <script src='${swaggerAssetsPath}/lib/swagger-oauth.js' type='text/javascript'></script>
  <script type="text/javascript">
    $(function () {
      window.swaggerUi = new SwaggerUi({
        url: "../swagger.json",
        dom_id: "swagger-ui-container",
        supportedSubmitMethods: ['get', 'post', 'put', 'delete'],
        onComplete: function(swaggerApi, swaggerUi){
          if(typeof initOAuth == "function") {
            /*
            initOAuth({
              clientId: "your-client-id",
              realm: "your-realms",
              appName: "your-app-name"
            });
            */
          }
          $('pre code').each(function(i, e) {
            hljs.highlightBlock(e)
          });
        },
        onFailure: function(data) {

        },
        docExpansion: "none",
        sorter : "alpha"
      });

      function addApiKeyAuthorization() {
        var key = $('#input_apiKey')[0].value;
        if(key && key.trim() != "") {
            swaggerUi.api.clientAuthorizations.add("api_key", new SwaggerClient.ApiKeyAuthorization("api_key", key, "query"));
        }
      }

        function addAuthorizationHeader() {
            var key = $('#input_authHeader')[0].value;
            if(key && key.trim() != "") {
                swaggerUi.api.clientAuthorizations.add("Custom Authorization", new SwaggerClient.ApiKeyAuthorization("Authorization", key, "header"));
            }
        }

      function addApiKeyKrakenAuthorization() {
        var key = $('#input_apiKeyKraken')[0].value;
        if(key && key.trim() != "") {
            swaggerUi.api.clientAuthorizations.add("api_key", new SwaggerClient.ApiKeyAuthorization("x-auth-id", key, "header"));
        }
      }

      function addApiSecretKrakenAuthorization() {
        var secret = $('#input_apiSecretKraken')[0].value;
        if(secret && secret.trim() != "") {
            swaggerUi.api.clientAuthorizations.add("api_secret", new SwaggerClient.ApiKeyAuthorization("x-auth-key", secret, "header"));
        }
      }



      $('#input_apiKey').change(function() {
        addApiKeyAuthorization();
      });

      $('#input_apiSecretKraken').change(function() {
        addApiSecretKrakenAuthorization();
      });

      $('#input_apiKeyKraken').change(function() {
        addApiKeyKrakenAuthorization();
      });

      $('#input_authHeader').change(function() {
          addAuthorizationHeader();
      });

      $('#input_headerSelect').change(function() {
          $('#header_0').hide();
          $('#header_1').hide();
          $('#header_2').hide();
          var toShow = $( this ).val();
          $('#header_'+toShow).show();
      });
      // if you have an apiKey you would like to pre-populate on the page for demonstration purposes...
      /*
        var apiKey = "myApiKeyXXXX123456789";
        $('#input_apiKey').val(apiKey);
        addApiKeyAuthorization();
      */

      window.swaggerUi.load();
  });
  </script>
</head>

<body class="swagger-section">
<div id='header'>
  <div class="swagger-ui-wrap">
    <a id="logo" href="http://swagger.io">swagger</a>
    <form id='api_selector'>
      <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/></div>
      <div class='input'>
          <select id="input_headerSelect">
              <option value="0">api_key</option>
              <option value="1">Auth Header</option>
              <option value="2" selected="selected">Kraken Auth</option>
          </select>
      </div>
      <div class='input' id="header_0" style="display: none;"><input placeholder="api_key" id="input_apiKey" name="apiKey" type="text"/></div>
      <div class='input' id="header_1" style="display: none;"><input placeholder="Basic ..." id="input_authHeader" name="authHeader" type="text"/></div>

      <div class='input' id="header_2">
        <input placeholder="x-auth-id" id="input_apiKeyKraken" name="apiKeyKraken" type="text"/>
        <input placeholder="x-auth-key" id="input_apiSecretKraken" name="apiSecretKraken" type="text"/>
      </div>

      <div class='input'><a id="explore" href="#">Explore</a></div>
    </form>
  </div>
</div>

<div id="message-bar" class="swagger-ui-wrap">&nbsp;</div>
<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
</body>
</html>
