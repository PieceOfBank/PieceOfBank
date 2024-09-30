import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import FamilyAccountList from "../../src/ui/components/Temporary/AccountList";
const TotalAccount = () => {

    return (
        <View className='flex-1 justify-center items-center'>
            <Text className='text-3xl my-1'>전체 계좌</Text>
            <FamilyAccountList />
        </View>
      );
}

export default TotalAccount;