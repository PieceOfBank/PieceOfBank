import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';

const NowAccount = () => {
    interface NowAccount {
        id: string;
        bank: string;
        accountNo: string;
    }
     // 임시 리스트 (거래)
     const nowBank: NowAccount = {id:'1', accountNo:'123-456-789', bank:'하나은행'}

    
     return(
        <View>
            <Link className='text-xl p-2' href={'/ward/accountList'}>{nowBank.bank} {nowBank.accountNo}</Link>
        </View>
    )



}

export default NowAccount