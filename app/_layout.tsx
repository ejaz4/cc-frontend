import { useFonts } from "expo-font";
import { Stack } from "expo-router";
import { StatusBar } from "expo-status-bar";
import "react-native-reanimated";

import { useIsSetup } from "@/libs/checkSetup";
import {
  DMSans_400Regular,
  DMSans_600SemiBold,
  DMSans_700Bold,
} from "@expo-google-fonts/dm-sans";
import { SetupScreen } from "./screens/setup";

export default function RootLayout() {
  // const colorScheme = useColorScheme();
  const [loaded] = useFonts({
    DMSans_400Regular,
    DMSans_600SemiBold,
    DMSans_700Bold,
  });
  const isSetup = useIsSetup();

  if (!loaded || isSetup === null) {
    // Async font loading only occurs in development.
    return null;
  }

  // useEffect(() => {});

  return (
    <>
      {!isSetup && <SetupScreen />}
      {isSetup && (
        // <ThemeProvider
        // value={colorScheme === "dark" ? DarkTheme : DefaultTheme}
        // >
        <>
          <Stack>
            <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
            <Stack.Screen name="+not-found" />
          </Stack>
          <StatusBar style="auto" />
        </>
        /* </ThemeProvider> */
      )}
    </>
  );
}
