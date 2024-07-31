import { FC } from "react";
import { AppBar, Box, IconButton, Toolbar, Typography } from "@mui/material"
import { Menu } from '@mui/icons-material'
import { drawerWidth } from "../constants";

interface MyAppBarProps {
    title: String;
}

const MyAppBar: FC<MyAppBarProps> = ({title}) => (
    <Box sx={{display: 'flex'}}>
        <AppBar
            position="fixed"
            sx={{
                width: `calc(100% - ${drawerWidth}px)`,
                // ml: drawerWidth
              }}
        >
            <Toolbar>
                <IconButton
                    color="inherit"
                    edge="start"
                    sx={{mr: 2}} //Puts space between elements
                >
                    <Menu />
                </IconButton>
                <Typography>
                   {title}
                </Typography>
            </Toolbar>
        </AppBar>
    </Box>
)

export default MyAppBar;