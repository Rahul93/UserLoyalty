package com.acko.template.secrets;

import com.acko.template.pojo.DBCredentials;

public interface ISecretManager {

    DBCredentials getDBCredentials(String dbname);
}
