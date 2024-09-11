import { Link } from "expo-router";
import { View, Text, ImageBackground } from 'react-native';

// MainPage

export default function MainScreen() {
  return (
    <ImageBackground source={require('../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text style={{ fontSize:30 }}>Welcome to the Home Page!</Text>
      <Text style={{ fontSize:30 }}>여기가 메인 페이지..</Text>
      <Link href={'/profile'}>이동하기</Link>
      <Link href={'/'}>로그아웃</Link>
      </View>
      </ImageBackground>
  );
}