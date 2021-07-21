/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useEffect} from 'react';
import {SafeAreaView, StatusBar, useColorScheme} from 'react-native';
import axios from 'axios';

import {Colors} from 'react-native/Libraries/NewAppScreen';
import messaging from '@react-native-firebase/messaging';
import {WebView} from 'react-native-webview';

const App = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
    flex: 1,
  };

  // for ios
  useEffect(() => {
    messaging()
      .requestPermission()
      .then(authStatus => {
        console.info('Apns status ===>', authStatus);
        if (
          authStatus === messaging.AuthorizationStatus.AUTHORIZED ||
          authStatus === messaging.AuthorizationStatus.PROVISIONAL
        ) {
          messaging()
            .getToken()
            .then(token => {
              // device token, FCM 服务器会自动收到token，无需应用发送
              console.log('messaging.getToken===>', token);

              axios
                .put(
                  'https://restaurant-portal.foodtruck-qa.com/cloud-message/replace',
                  {new_message_token: token},
                )
                .catch(err => {
                  console.info(err);
                });
            })
            .catch(err => {
              console.info('get token err ===>', err);
            });

          // messaging().onTokenRefresh(token => {
          //   console.log("messaging.onTokenRefresh ===>", token)
          // })
          // // 应用前台接收通知
          // fcmUnsubscribe = messaging().onMessage(async remoteMessage => {
          //   console.log("New message arrive ===>", remoteMessage)
          // })
        }
      })
      .catch(err => {
        console.info('Messaging requestPermission err ===>', err);
      });
  }, []);

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <WebView
        style={{flex: 1}}
        source={{uri: 'https://restaurant-portal.foodtruck-qa.com/'}}
      />
    </SafeAreaView>
  );
};

export default App;
