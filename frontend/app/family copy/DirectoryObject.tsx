import { View, Text, SafeAreaView, TextInput, Button, TouchableOpacity } from "react-native";
import { Link } from "expo-router";
import { useState } from "react";
import { useRouter } from 'expo-router';
import Toast from 'react-native-toast-message';
import ObjectInput from "../../src/ui/components/Temporary/ObjectInput";

const DirectoryObject = () => {
    const [oneLimit, setOneLimit] = useState<string>("");
    const [todayLimit, setTodayLimit] = useState<string>("");
    const router = useRouter()
  /* 알림팝업 Logic */
  const handleBtn = () => {
    Toast.show({
      type: 'success',
      text1: 'Hello',
      text2: 'This is some something 👋'
    })
  }

  return (
    <View className='flex-1'>
      <View className='justify-center items-center p-4'>
        <ObjectInput />

      </View>
    </View>
  );
};

export default DirectoryObject;