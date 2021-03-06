/*
 * Copyright (c) 2017 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.here.account.auth.provider;

import java.util.Properties;

import com.here.account.auth.OAuth1ClientCredentialsProvider;
import com.here.account.http.HttpConstants.HttpMethods;
import com.here.account.http.HttpProvider.HttpRequestAuthorizer;
import com.here.account.oauth2.ClientAuthorizationRequestProvider;
import com.here.account.oauth2.ClientCredentialsProvider;

/**
 * A {@link ClientCredentialsProvider} that pulls credential values from the System Properties.
 */
public class FromSystemProperties extends ClientCredentialsGrantRequestProvider
implements ClientAuthorizationRequestProvider {
    public FromSystemProperties() {
    }

    protected ClientCredentialsProvider getDelegate() {
        Properties properties = System.getProperties();
        return getClientCredentialsProviderWithDefaultTokenEndpointUrl(properties);
    }

    private static final String DEFAULT_TOKEN_ENDPOINT_URL = "https://account.api.here.com/oauth2/token";

    static ClientCredentialsProvider getClientCredentialsProviderWithDefaultTokenEndpointUrl(Properties properties) {
        return new OAuth1ClientCredentialsProvider(
                properties.getProperty(OAuth1ClientCredentialsProvider.FromProperties.TOKEN_ENDPOINT_URL_PROPERTY, DEFAULT_TOKEN_ENDPOINT_URL),
                properties.getProperty(OAuth1ClientCredentialsProvider.FromProperties.ACCESS_KEY_ID_PROPERTY),
                properties.getProperty(OAuth1ClientCredentialsProvider.FromProperties.ACCESS_KEY_SECRET_PROPERTY)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTokenEndpointUrl() {
        return getDelegate().getTokenEndpointUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpRequestAuthorizer getClientAuthorizer() {
        return getDelegate().getClientAuthorizer();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpMethods getHttpMethod() {
        return HttpMethods.POST;
    }

}
