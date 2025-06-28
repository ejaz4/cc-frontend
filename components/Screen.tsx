import { StyleSheet, View, Text, StatusBar } from "react-native";

export const ActivityWithTitle = ({
    title,
    children
}: {
    title: string;
    children: React.ReactNode;
}) => {
    return (
        <View style={screenStyles.screen}>
            <View >
                <Text style={screenStyles.title}>{title}</Text>
            </View>
            <View >
                {children}
            </View>
        </View>
    )
}

const screenStyles = StyleSheet.create({
    screen: {
        display: "flex",
        backgroundColor: "#fff",
        padding: 16,
        gap: 16,
        paddingTop: (StatusBar.currentHeight ? StatusBar.currentHeight : 10) + 64,
        minHeight: "100%",
    },
    title: {
        fontSize: 24,
        fontFamily: "DMSans_700Bold",
        marginBottom: 16,
    }
})

export const screenKit = StyleSheet.create({
    screenLayout: {
        display: "flex",
        flexDirection: "column",
        gap: 16
    }
})