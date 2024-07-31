import { FC } from "react";
import { AppBar, Box, IconButton, Toolbar, Typography } from "@mui/material"
import { Menu } from '@mui/icons-material'

interface MyAppBarProps {
    title: String;
}

const MyAppBar: FC<MyAppBarProps> = ({title}) => (
    <Box sx={{flexGrow: 1}}>
        <AppBar>
            <Toolbar>
                <IconButton
                    color="inherit"
                    edge="start"
                    sx={{mr: 2}}
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